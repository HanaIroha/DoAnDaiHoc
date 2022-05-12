import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BannersComponent } from './banners/banners.component';
import { CreateBannerComponent } from './banners/create-banner/create-banner.component';
import { CategoriesComponent } from './categories/categories.component';
import { CreateCategoriesComponent } from './categories/create-categories/create-categories.component';
import { UpdateCategoryComponent } from './categories/update-category/update-category.component';
import { OrderdetailsComponent } from './orders/orderdetails/orderdetails.component';
import { OrdersComponent } from './orders/orders.component';
import { CreatePaymentComponent } from './payments/create-payment/create-payment.component';
import { PaymentsComponent } from './payments/payments.component';
import { CreateProducerComponent } from './producers/create-producer/create-producer.component';
import { ProducersComponent } from './producers/producers.component';
import { CreateProductComponent } from './products/create-product/create-product.component';
import { CreateImageComponent } from './products/image/create-image/create-image.component';
import { ImageComponent } from './products/image/image.component';
import { ProductsComponent } from './products/products.component';
import { UserComponent } from './user/user.component';
import { DetailUseroderhistoryComponent } from './user/userorderhistory/detail-useroderhistory/detail-useroderhistory.component';
import { UserorderhistoryComponent } from './user/userorderhistory/userorderhistory.component';

/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild([
      {
        path: 'user-management',
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
        data: {
          pageTitle: 'userManagement.home.title',
        },
      },
      {
        path: 'docs',
        loadChildren: () => import('./docs/docs.module').then(m => m.DocsModule),
      },
      {
        path: 'configuration',
        loadChildren: () => import('./configuration/configuration.module').then(m => m.ConfigurationModule),
      },
      {
        path: 'health',
        loadChildren: () => import('./health/health.module').then(m => m.HealthModule),
      },
      {
        path: 'logs',
        loadChildren: () => import('./logs/logs.module').then(m => m.LogsModule),
      },
      {
        path: 'metrics',
        loadChildren: () => import('./metrics/metrics.module').then(m => m.MetricsModule),
      },
      {
        path: 'category',
        component: CategoriesComponent,
      },
      {
        path: 'category/new',
        component: CreateCategoriesComponent,
      },
      {
        path: 'category/update/:id',
        component: UpdateCategoryComponent,
      },
      {
        path: 'payment',
        component: PaymentsComponent,
      },
      {
        path: 'payment/new',
        component: CreatePaymentComponent,
      },
      {
        path: 'payment/edit/:id',
        component: CreatePaymentComponent,
      },
      {
        path: 'producer',
        component: ProducersComponent,
      },
      {
        path: 'producer/new',
        component: CreateProducerComponent,
      },
      {
        path: 'producer/edit/:id',
        component: CreateProducerComponent,
      },
      {
        path: 'product',
        component: ProductsComponent,
      },
      {
        path: 'product/new',
        component: CreateProductComponent,
      },
      {
        path: 'product/edit/:id',
        component: CreateProductComponent,
      },
      {
        path: 'product/edit/:id/image',
        component: ImageComponent,
      },
      {
        path: 'product/edit/:id/image/new',
        component: CreateImageComponent,
      },
      {
        path: 'order',
        component: OrdersComponent,
      },
      {
        path: 'order/detail/:id',
        component: OrderdetailsComponent,
      },
      {
        path: 'user',
        component: UserComponent,
      },
      {
        path: 'user/orderhistory',
        component: UserorderhistoryComponent,
      },
      {
        path: 'user/orderhistory/detail/:id',
        component: DetailUseroderhistoryComponent,
      },
      {
        path: 'banner',
        component: BannersComponent,
      },
      {
        path: 'banner/new',
        component: CreateBannerComponent,
      },
      {
        path: 'banner/edit/:id',
        component: CreateBannerComponent,
      },
      /* jhipster-needle-add-admin-route - JHipster will add admin routes here */
    ]),
  ],
})
export class AdminRoutingModule {}
