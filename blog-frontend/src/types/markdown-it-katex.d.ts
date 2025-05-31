declare module '@iktakahiro/markdown-it-katex' {
  import MarkdownIt from 'markdown-it';
  
  function katex(md: MarkdownIt, options?: {
    throwOnError?: boolean;
    errorColor?: string;
    delimiters?: {
      inline?: [string, string];
      display?: [string, string];
    };
  }): void;
  
  export default katex;
} 