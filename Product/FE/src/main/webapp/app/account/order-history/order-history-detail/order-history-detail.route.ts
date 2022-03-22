import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderHistoryDetailComponent } from './order-history-detail.component';

export const orderHistoryDetailRoute: Route = {
  path: 'orderhistory/detail/:id',
  component: OrderHistoryDetailComponent,
  data: {
    pageTitle: 'OrderHistoryDetail',
  },
  canActivate: [UserRouteAccessService],
};
