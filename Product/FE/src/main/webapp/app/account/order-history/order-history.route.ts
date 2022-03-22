import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderHistoryComponent } from './order-history.component';

export const orderHistoryRoute: Route = {
  path: 'orderhistory',
  component: OrderHistoryComponent,
  data: {
    pageTitle: 'OrderHistory',
  },
  canActivate: [UserRouteAccessService],
};
