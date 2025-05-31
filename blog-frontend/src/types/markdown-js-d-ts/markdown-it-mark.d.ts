declare module 'markdown-it-mark' {
    import MarkdownIt from 'markdown-it';
    function mark(md: MarkdownIt): void;
    export = mark;
  }
  