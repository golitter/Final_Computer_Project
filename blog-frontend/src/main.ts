import { createApp } from 'vue'

import App from './App.vue'
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
import './assets/css/index.scss'
import './assets/css/code-block.scss' // Code block styles
import 'katex/dist/katex.min.css'

import FontAwesomeIcon from "./utils/awesomeIcon";

// Import router
import router from './router'
// Import pinia
import { createPinia } from 'pinia';
// @ts-ignore Ignore TypeScript type checking for this file (otherwise there will be red warnings and build will fail)
import en from 'element-plus/dist/locale/en.mjs'

// Markdown editor
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css'; // Base styles
// @ts-ignore
import githubTheme from '@kangc/v-md-editor/lib/theme/github';
import '@kangc/v-md-editor/lib/theme/style/github.css'; // GitHub theme styles
import hljs from 'highlight.js'; // Code highlighting tool
// Use GitHub theme
VMdEditor.use(githubTheme, {
    Hljs: hljs,
  });
const app = createApp(App)
const pinia = createPinia();
app.use(ElementPlus, {
    locale: en
})
app.use(router)
app.use(pinia);

app.component("font-awesome-icon", FontAwesomeIcon)

app.use(VMdEditor); // Register Markdown editor
app.mount('#app')
