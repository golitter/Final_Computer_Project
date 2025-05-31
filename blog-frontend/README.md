# Design and Implementation of a Secure Personal Blog System Based on SpringBoot3 and Vue3-TypeScript

> This document is for the frontend part

## Custom Configuration

Configure personal information in the `\src\stores\admin.ts` file:

- Username
- Signature
- Avatar image location
- Personal IDs for websites/software like GitHub/Email/CSDN

## Running

```shell
pnpm install
```

```shell
npm run dev
```

The following message indicates successful frontend startup:

```shell
> golemon-blog@0.0.0 dev
> vite

  VITE v5.4.10  ready in 548 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h + enter to show help
```

Enter `http://localhost:5173/` in your browser to view the frontend display.



## API doc

```shell
npx @redocly/cli preview-docs docs/openapi/openapi.yaml
```



## Postman Test document (online)

```url
https://.postman.co/workspace/My-Workspace~e2b2be76-d6bf-453f-8ad0-2f6046dd9a14/collection/36161327-93aa1099-73fa-4a49-965b-dfb9874ac192?action=share&creator=36161327
```

