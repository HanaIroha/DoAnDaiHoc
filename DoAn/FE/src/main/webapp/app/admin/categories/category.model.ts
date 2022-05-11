export interface ICategory {
    idCategory?: number;
    name?: string;
    parentId?: number | null;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class Category implements ICategory {
    constructor(
        public idCategory?: number,
        public name?: string,
        public parentId?: number | null,
        public createdAt?: Date,
        public updatedAt?: Date
    ) {}
  }
  