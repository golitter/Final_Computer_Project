/**
 * Archive statistics item interface definition
 */
export interface ArchiveCountItem {
    date: string;
    count: number;
}
  
/**
 * Archive list item interface definition
 */
export interface ArchiveItem {
    id: number;
    title: string;
    date: string;
    // More properties can be added based on actual data structure
}