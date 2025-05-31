import 'prism-themes/themes/prism-one-dark.css'; // Import One Dark theme
import Prism from 'prismjs';
import ClipboardJS from 'clipboard';
import $ from 'jquery';

// Import Prism.js supported languages
import 'prismjs/components/prism-javascript.min.js';
import 'prismjs/components/prism-python.min.js';
import 'prismjs/components/prism-java.min.js';
import 'prismjs/components/prism-css.min.js';
import 'prismjs/components/prism-sql.min.js';
import 'prismjs/components/prism-c.min.js';
import 'prismjs/components/prism-cpp.min.js';
import 'prismjs/components/prism-bash.min.js';
import 'prismjs/components/prism-markdown.min.js';

// Highlight code blocks
function highlightCode(element: { querySelectorAll: (arg0: string) => any; }) {
    const codeEls = element.querySelectorAll('pre code'); // Find code elements in pre
    codeEls.forEach((el: any) => {
        Prism.highlightElement(el);  // Use Prism.js to highlight code
    });
    // Find all inline code elements
    const inlineCodeEls = element.querySelectorAll('code');
    inlineCodeEls.forEach((el: any) => {
        Prism.highlightElement(el);  // Use Prism.js to highlight inline code
    });
}

// Add copy button to code blocks
function buildCopyButton(element: any) {
    const $pres = $(element).find('pre');
    if (!$pres.length) return;

    $pres.each(function () {
        const text = $(this).children("code").text(); // Get code content

        // Create wrapper container and copy button
        const wrapper = $('<div class="code-wrapper"></div>');
        const btn = $('<span class="copy">copy</span>').attr("data-clipboard-text", text);

        // Wrap existing code block
        $(this).wrap(wrapper);
        $(this).parent().append(btn); // Add button to wrapper container

        const clipboard = new ClipboardJS(btn[0]); // Initialize ClipboardJS
        clipboard.on("success", function () {
            btn.addClass("copyed").text("copyed");
            setTimeout(function () {
                btn.text("copy").removeClass("copyed");
            }, 1000);
        });
        clipboard.on("error", function () {
            btn.text("Copy failed");
        });
    });
}

// Create code block and apply highlighting, line numbers, and copy button
function buildCodeBlock(selector: any) {
    const elements = document.querySelectorAll(selector); // Find all elements that need processing
    elements.forEach((element) => {
        highlightCode(element);   // Highlight code
        buildCopyButton(element); // Add copy button
    });
}

export default buildCodeBlock;
