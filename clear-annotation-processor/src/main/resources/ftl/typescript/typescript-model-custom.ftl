/// <reference path='../refs.ts'/>

/**
 * Generated by Typescript generator.
 *
 * Used for customizing generated code.
 * You can freely update this class.
 */

module ${package} {
  'use strict';

  export class ${classSimpleName} extends ${classSimpleName}_ {

    static fromObject(source):${classSimpleName} {
      var obj = new ${classSimpleName}();
      obj.copyFromObject(source);
      return obj;
    }
  }
}