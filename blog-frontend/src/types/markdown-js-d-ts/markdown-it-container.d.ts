declare module 'markdown-it-container' {
    import MarkdownIt from 'markdown-it';
    function container(md: MarkdownIt): void;
    export = container;
  }
  