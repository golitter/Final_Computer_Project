// Define article type
export interface Article {
    id: number;
    title: string;
    content: string;
    categoryId: number; 
    categoryName: string;
    summary: string; // Summary
    tags?: string[]; // Tag list, corresponding to Java List<String>
    thumbnail?: string; // Thumbnail
    isDraft: boolean; // Must specify whether it is a draft
    createTime?: string; // Creation time
    viewCount?: number; // View count
}

export interface ArticleList {
    total: number;
    rows: Article[];
}
export interface ArticleResponse {
    data: ArticleList;
    message: string;        // Response message
    status: number;         // Status code
}
// Define hot article return type
export interface HotArticle {
    id: number;
    title: string;
    createTime: string;
    viewCount: number;
}

interface tag {
    id: number;
    name: string;
}
// Define interface type
export interface ArticleDetails {
    title?: string;
    createTime: string;
    content?: string;
    viewCount?: number;
    id?: number;
    tags?: tag[];
}

export interface ArticleMeta {
    id: number;
    title: string;
    // thumbnail?: string; // TODO: Delete
    creatTime: string;
    viewCount: number;
}

export interface Comment {
    id: string;
    userName: string;
    content: string;
}

export interface ToolbarOptions {
    bold: boolean;
    italic: boolean;
    header: boolean;
    underline: boolean;
    strikethrough: boolean;
    mark: boolean;
    superscript: boolean;
    subscript: boolean;
    quote: boolean;
    ol: boolean;
    ul: boolean;
    link: boolean;
    imagelink: boolean;
    code: boolean;
    table: boolean;
    fullscreen: boolean;
    help: boolean;
    navigation: boolean;
    alignleft: boolean;
    aligncenter: boolean;
    alignright: boolean;
    subfield: boolean;
    preview: boolean;
}