declare module 'markdown-it-task-lists' {
    import MarkdownIt from 'markdown-it';
    function taskLists(md: MarkdownIt): void;
    export = taskLists;
  }
  