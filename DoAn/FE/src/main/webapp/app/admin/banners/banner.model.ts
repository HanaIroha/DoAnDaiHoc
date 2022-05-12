export interface IBanner {
    idBanner?: number;
    upperTitle?: string;
    mainTitle?: string;
    image?: string;
    linkNavigate?: string;
  }
  
  export class Banner implements IBanner {
    constructor(
        public idBanner?: number,
        public upperTitle?: string,
        public mainTitle?: string,
        public image?: string,
        public linkNavigate?: string,
    ) {}
  }
  