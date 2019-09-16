export interface DynamicServiceSpecification {
  exposedEventTriggers: Record<string, {
    buttonLabel: string,
    description: string
  }>;
}
