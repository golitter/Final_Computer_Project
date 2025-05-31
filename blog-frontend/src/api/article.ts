import request from '../utils/request';
import { Article, HotArticle, ArticleResponse } from '../types/article';

// Define article details return type
interface ArticleDetails extends Article {
    // Add other properties for article details
}

// Define return type for getting hot article list
type GetHotArticleListResponse = Promise<HotArticle[]>;

/**
 * Get hot articles
 * @returns promise
 */
function getHotArticleList(): GetHotArticleListResponse {
    return request.get('/article/hotArticleList');
}

/**
 * Get random articles
 * @returns promise
 */
function getRandomArticleList(): GetHotArticleListResponse {
    return request.get('/article/randomArticleList');
}

/**
 * Get total article count
 * @returns promise
 */
function getArticleCount(): Promise<number> {
    return request.get('/article/count');
}

/**
 * Get published articles
 * @param pageNum Current page number
 * @param pageSize Number of articles per page
 * @param categoryId Article category ID (optional)
 * @param tagId Article tag ID (optional)
 * @param date Article publish date in format `yyyy/MM`, e.g. `2021/1` or `2021/02` (optional)
 * @param title Article title keyword for fuzzy search (optional)
 * @returns promise
 */
function getPostArticleList(
    pageNum: number,
    pageSize: number,
    categoryId?: number,
    tagId?: number,
    date?: string,
    title?: string
): Promise<ArticleResponse> {
    const params = { pageNum, pageSize, categoryId, tagId, date, title };
    return request({
        url: '/article/articleList',
        params
    });
}

/**
 * Get article details
 * @param id Article ID
 * @returns promise
 * @TODO Why use Promise<ArticleDetails> instead of Promise<Article>?
 */
function getArticleDetails(id: number): Promise<ArticleDetails> {
    return request.get(`/article/${id}`).then(response => {
      // Ensure response.data exists and is of type ArticleDetails
      if (response.data) {
        console.log("response.data", response.data);
        return response.data as ArticleDetails;
      } else {
        // If not ArticleDetails type, throw error
        throw new Error('Invalid response format');
      }
    });
  }

/**
 * Update article view count
 * @param id Article ID
 * @returns promise
 */
function updateViewCount(id: number): Promise<void> {
    return request({
        url: `/article/updateViewCount/${id}`,
        method: "put",
    });
}

/**
 * Get previous and next articles
 * @param id Article ID
 * @returns promise
 */
function getPreviousNextArticle(id: number): Promise<{ data: { previous: Article; next: Article } }> {
    return request.get(`/article/previousNextArticle/${id}`);
}

/**
 * Add article
 * @param article Article
 * @returns promise
 */
function addArticle(article: Article): Promise<number> {
    return request({
        url: "article/add",
        method: "post",
        data: article,
        needAuthentication: true
    });
}

/**
 * Edit article
 * @param article Article
 * @returns promise
 */
function editArticle(article: Article): Promise<void> {
    return request({
        url: "/article/edit",
        method: "put",
        data: article,
        needAuthentication: true
    });
}

/**
 * Delete article
 * @param id Article ID
 * @returns promise
 */
function deleteArticle(id: number): Promise<void> {
    return request({
        url: `/article/${id}`,
        method: "delete",
        needAuthentication: true
    });
}

export {
    getHotArticleList,
    getRandomArticleList,
    getArticleCount,
    getPostArticleList,
    getArticleDetails,
    updateViewCount,
    getPreviousNextArticle,
    addArticle,
    editArticle,
    deleteArticle
};
