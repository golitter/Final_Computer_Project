A **schema** is a fundamental concept in the database domain. Some databases combine schemas with users, but PostgreSQL clearly distinguishes between them.

In essence, a schema in a database acts like a **namespace or directory**. Different schemas can contain objects (like tables or functions) with the same name without conflict. This design simplifies management, and as long as proper permissions are in place, objects across schemas can reference each other.

In PostgreSQL, a single database can contain multiple schemas, and each schema holds its own set of tables, functions, and other objects. However, this structure may vary across different databases.

**Advantages of schemas:**

- Enable multiple users to work within the same database without interfering with each other.
- Group database objects logically for easier management.
- Allow third-party applications to reside in separate schemas, avoiding naming conflicts with existing objects.

**Syntax examples:**

```
sqlCopyEdit-- Create a schema
CREATE SCHEMA schemaname [ AUTHORIZATION username ... ];

-- List existing schemas
\dn

-- Drop a schema
DROP SCHEMA schemaname;
```

**Schema Search Path:**

Writing fully qualified object names can be cumbersome, and hardcoding schema names in applications is not recommended. Typically, objects are referred to by their unqualified names. PostgreSQL determines the target object by searching through a **search path**—a list of schemas to be searched in order. The first match found is used. If no matching object is found in the search path, an error is thrown, even if the object exists in another schema.

The first schema in the search path is known as the **current schema**, and it is also the default location for new objects created without a specified schema.

```
sqlCopyEdit-- Show current search path
SHOW search_path;

-- Set default schema search path
SET search_path TO selfschema, public;
```

**Schema Permissions:**

By default, users **cannot access objects** in schemas they do not own. To enable access, the **USAGE** privilege must be granted on the schema.

```
sqlCopyEdit-- Revoke CREATE permission from all users
REVOKE CREATE ON SCHEMA schemaname FROM PUBLIC;
-- 'schemaname' is the schema name; 'PUBLIC' refers to all users.
```

**Portability Considerations:**

According to the SQL standard, objects within the same schema cannot be owned by different users, and the concept of a `public` schema does not exist. Some database systems do not support schemas at all.