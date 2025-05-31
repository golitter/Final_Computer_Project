module.exports = {
    // Runtime environment
     "env": { 
         "browser": true, // Browser environment
         "es2021": true, // ES2021
     },
     // Rule inheritance
     "extends": [ 
        // All rules are disabled by default, this configuration enables recommended rules
        // For example: functions cannot have duplicate names, objects cannot have duplicate keys
         "eslint:recommended",
         // Vue3 syntax rules
         "plugin:vue/vue3-essential",
         // TypeScript syntax rules
         "plugin:@typescript-eslint/recommended"
     ],
     // Specify processors for specific file types
     "overrides": [
     ],
     // Specify parser
     // Esprima - default parser
     // Babel-ESLint - babel parser
     // @typescript-eslint/parser - TypeScript parser
     "parser": "@typescript-eslint/parser",
     // Specify parser options
     "parserOptions": {
         "ecmaVersion": "latest", // Validate latest ECMA version
         "sourceType": "module" // Set to "script" (default) or "module" for ECMAScript modules
     },
     // ESLint supports using third-party plugins. You must install them using npm before use
     // The eslint-plugin- prefix can be omitted from the plugin name
     "plugins": [
         "vue",
         "@typescript-eslint"
     ],
     // ESLint rules
     "rules": {
     }
 }