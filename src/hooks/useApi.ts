import { useState, useEffect, useCallback } from 'react';
import { useAuth } from '@/contexts/AuthContext';

interface UseApiOptions<T> {
  enabled?: boolean;
  fallbackData?: T;
  onError?: (error: Error) => void;
}

interface UseApiResult<T> {
  data: T | undefined;
  isLoading: boolean;
  error: Error | null;
  refetch: () => Promise<void>;
}

export function useApi<T>(
  apiCall: () => Promise<T>,
  options: UseApiOptions<T> = {}
): UseApiResult<T> {
  const { enabled = true, fallbackData, onError } = options;
  const { useMockAuth } = useAuth();
  
  const [data, setData] = useState<T | undefined>(fallbackData);
  const [isLoading, setIsLoading] = useState(enabled);
  const [error, setError] = useState<Error | null>(null);

  const fetchData = useCallback(async () => {
    if (useMockAuth) {
      // If using mock auth, just use fallback data
      setData(fallbackData);
      setIsLoading(false);
      return;
    }
    
    setIsLoading(true);
    setError(null);
    
    try {
      const result = await apiCall();
      setData(result);
    } catch (err) {
      const error = err instanceof Error ? err : new Error('An error occurred');
      setError(error);
      setData(fallbackData);
      onError?.(error);
    } finally {
      setIsLoading(false);
    }
  }, [apiCall, fallbackData, onError, useMockAuth]);

  useEffect(() => {
    if (enabled) {
      fetchData();
    }
  }, [enabled, fetchData]);

  return {
    data,
    isLoading,
    error,
    refetch: fetchData,
  };
}

// Mutation hook for POST/PUT/DELETE operations
interface UseMutationOptions<T, V> {
  onSuccess?: (data: T) => void;
  onError?: (error: Error) => void;
}

interface UseMutationResult<T, V> {
  mutate: (variables: V) => Promise<T | undefined>;
  data: T | undefined;
  isLoading: boolean;
  error: Error | null;
  reset: () => void;
}

export function useMutation<T, V = void>(
  mutationFn: (variables: V) => Promise<T>,
  options: UseMutationOptions<T, V> = {}
): UseMutationResult<T, V> {
  const { onSuccess, onError } = options;
  
  const [data, setData] = useState<T | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const mutate = async (variables: V): Promise<T | undefined> => {
    setIsLoading(true);
    setError(null);
    
    try {
      const result = await mutationFn(variables);
      setData(result);
      onSuccess?.(result);
      return result;
    } catch (err) {
      const error = err instanceof Error ? err : new Error('An error occurred');
      setError(error);
      onError?.(error);
      return undefined;
    } finally {
      setIsLoading(false);
    }
  };

  const reset = () => {
    setData(undefined);
    setError(null);
    setIsLoading(false);
  };

  return {
    mutate,
    data,
    isLoading,
    error,
    reset,
  };
}
