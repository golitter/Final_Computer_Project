import MarkdownIt, { Options } from "markdown-it";
import toc from "markdown-it-toc-done-right";
import deflist from "markdown-it-deflist";
import abbr from "markdown-it-abbr";
import footnote from "markdown-it-footnote";
import ins from "markdown-it-ins";
import mark from "markdown-it-mark";
import taskLists from "markdown-it-task-lists";
import container from "markdown-it-container";
import mermaid from "@DatatracCorporation/markdown-it-mermaid";
import katex from "@iktakahiro/markdown-it-katex";

// Define MarkdownIt configuration options type
const config: Options = {
  html: true,
  xhtmlOut: true,
  breaks: true,
  langPrefix: "lang-",
  linkify: true,
  typographer: true,
  quotes: '""""'
};

// Create MarkdownIt instance
const markdownIt = new MarkdownIt(config);

// Use plugins
markdownIt
  .use(deflist)
  .use(abbr)
  .use(footnote)
  .use(ins)
  .use(mark)
  .use(taskLists)
  .use(container)
  .use(container, "hljs-left")
  .use(container, "hljs-center")
  .use(container, "hljs-right")
  .use(toc)
  .use(mermaid)
  .use(katex, {
    throwOnError: false,
    errorColor: '#cc0000',
  });

export default markdownIt;
