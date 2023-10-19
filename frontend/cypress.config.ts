import { defineConfig } from 'cypress';

export default defineConfig({
  env: {
    BASE_URL: 'https://dev.donggle.blog/api',
  },
  e2e: {
    baseUrl: 'http://localhost:3000',
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
