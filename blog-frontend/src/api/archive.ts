import request from '../utils/request';
import { ArchiveCountItem, ArchiveItem } from '../types/archive';

/**
 * Get archive and its article count statistics
 * @param {number} pageNum Current page number
 * @param {number} pageSize Items per page
 * @returns promise
 */
function getArchiveCountList(pageNum: number, pageSize: number): Promise<{ rows: ArchiveCountItem[] }> {
  return request({
      url: '/archive/archiveCountList',
      params: {
          pageNum,
          pageSize,
      },
  }).then(response => {
      if (response && response.data && Array.isArray(response.data.rows)) {
          return response.data; // Return data that matches the interface definition
      } else {
          // If data format doesn't match expectations, throw error or return empty array
          throw new Error('Invalid data format');
      }
  });
}

/**
 * Get archive list
 * @param {number} pageNum Current page number
 * @param {number} pageSize Items per page
 * @returns promise
 */
function getArchiveList(pageNum: number, pageSize: number): Promise<{ rows: ArchiveItem[] }> {
  return request({
    url: '/archive/archiveList',
    params: {
      pageNum,
      pageSize,
    },
  });
}

export { getArchiveCountList, getArchiveList };
