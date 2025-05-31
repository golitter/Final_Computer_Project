When collecting form data with `v-model`:

- For `<input type="text" />`, `v-model` binds to the `value` property, meaning it collects the text entered by the user.
- For `<input type="radio" />`, `v-model` also collects the `value`, and each radio button should be assigned a `value` attribute.
- For `<input type="checkbox" />`:
  - If no `value` attribute is configured, `v-model` collects the `checked` state — a boolean indicating whether the box is checked.
  - If a `value` attribute is configured:
    - If the initial value bound by `v-model` is not an array, it collects the `checked` state (boolean).
    - If the initial value is an array, it collects an array of `value`s for the checked boxes.

**Three modifiers for `v-model`:**

- `lazy`: Data is collected only after the input loses focus.
- `number`: Converts the input string to a valid number.
- `trim`: Automatically trims leading and trailing whitespace from the input.