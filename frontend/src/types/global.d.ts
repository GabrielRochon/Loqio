/**
 * Global type declarations for the application.
 *
 * This file provides TypeScript type definitions for modules that don't have
 * built-in type declarations, such as SCSS and CSS files. By declaring these
 * modules, we eliminate TypeScript errors when importing stylesheets while
 * maintaining type safety.
 */

declare module '*.scss' {
  const content: { [className: string]: string };
  export default content;
}

declare module '*.css' {
  const content: { [className: string]: string };
  export default content;
}
