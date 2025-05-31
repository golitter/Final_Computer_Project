// @see https://eslint.bootcss.com/docs/rules/

module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true,
    jest: true,
  },
  /* Specify how to parse syntax */
  parser: 'vue-eslint-parser',
  /** Syntax parsing configuration with lower priority than parser */
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    parser: '@typescript-eslint/parser',
    jsxPragma: 'React',
    ecmaFeatures: {
      jsx: true,
    },
  },
  /* Inherit existing rules */
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-essential',
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended',
  ],
  plugins: ['vue', '@typescript-eslint'],
  /*
   * "off" or 0    ==>  turn off the rule
   * "warn" or 1   ==>  turn on the rule as a warning (does not affect code execution)
   * "error" or 2  ==>  turn on the rule as an error (code cannot execute, interface reports error)
   */
  rules: {
    // eslint (https://eslint.bootcss.com/docs/rules/)
    'no-var': 'error', // Require let or const instead of var
    'no-multiple-empty-lines': ['warn', { max: 1 }], // Do not allow multiple empty lines
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-unexpected-multiline': 'error', // Disallow unexpected multiline
    'no-useless-escape': 'off', // Disallow unnecessary escape characters

    // typeScript (https://typescript-eslint.io/rules)
    '@typescript-eslint/no-unused-vars': 'error', // Disallow unused variables
    '@typescript-eslint/prefer-ts-expect-error': 'error', // Disallow @ts-ignore
    '@typescript-eslint/no-explicit-any': 'off', // Disallow any type
    '@typescript-eslint/no-non-null-assertion': 'off',
    '@typescript-eslint/no-namespace': 'off', // Disallow custom TypeScript modules and namespaces
    '@typescript-eslint/semi': 'off',

    // eslint-plugin-vue (https://eslint.vuejs.org/rules/)
    'vue/multi-word-component-names': 'off', // Require component names to always be hyphenated words
    'vue/script-setup-uses-vars': 'error', // Prevent variables used in <script setup> from being marked as unused in <template>
    'vue/no-mutating-props': 'off', // Disallow mutation of component props
    'vue/attribute-hyphenation': 'off', // Enforce attribute naming style for custom components in template
  },
}
