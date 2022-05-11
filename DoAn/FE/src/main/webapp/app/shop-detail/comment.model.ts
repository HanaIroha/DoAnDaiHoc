export interface IComment {
    idComment?: number;
    login?: string;
    idProduct?: number;
    idParentComment?: number;
    content?: string;
    createdAt?: Date;
    updatedAt?: Date;
  }
  
  export class Comment implements IComment {
    constructor(
        public idComment?: number,
        public login?: string,
        public idProduct?: number,
        public idParentComment?: number,
        public content?: string,
        public createdAt?: Date,
        public updatedAt?: Date,
    ) {}
  }
  