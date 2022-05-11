import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgxWebstorageModule } from 'ngx-webstorage';
import dayjs from 'dayjs/esm';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import './config/dayjs';
import { SharedModule } from 'app/shared/shared.module';
import { TranslationModule } from 'app/shared/language/translation.module';
import { AppRoutingModule } from './app-routing.module';
import { HomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { CategoriesComponent } from './admin/categories/categories.component';
import { CreateCategoriesComponent } from './admin/categories/create-categories/create-categories.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdateCategoryComponent } from './admin/categories/update-category/update-category.component';
import { PaymentsComponent } from './admin/payments/payments.component';
import { CreatePaymentComponent } from './admin/payments/create-payment/create-payment.component';
import { ProducersComponent } from './admin/producers/producers.component';
import { CreateProducerComponent } from './admin/producers/create-producer/create-producer.component';
import { ProductsComponent } from './admin/products/products.component';
import { CreateProductComponent } from './admin/products/create-product/create-product.component';
import { CommonModule, CurrencyPipe} from '@angular/common';
import { ImageComponent } from './admin/products/image/image.component';
import { CreateImageComponent } from './admin/products/image/create-image/create-image.component';
import { ShopComponent } from './shop/shop.component';
import { ShopDetailComponent } from './shop-detail/shop-detail.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { OrdersComponent } from './admin/orders/orders.component';
import { OrderdetailsComponent } from './admin/orders/orderdetails/orderdetails.component';
import { UserComponent } from './admin/user/user.component';
import { UserorderhistoryComponent } from './admin/user/userorderhistory/userorderhistory.component';
import { DetailUseroderhistoryComponent } from './admin/user/userorderhistory/detail-useroderhistory/detail-useroderhistory.component';


@NgModule({
  imports: [
    BrowserModule,
    SharedModule,
    HomeModule,
    FormsModule,
    ReactiveFormsModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppRoutingModule,
    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    TranslationModule,
    CarouselModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'en' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    httpInterceptorProviders,
    CurrencyPipe,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    CategoriesComponent,
    CreateCategoriesComponent,
    UpdateCategoryComponent,
    PaymentsComponent,
    CreatePaymentComponent,
    ProducersComponent,
    CreateProducerComponent,
    ProductsComponent,
    CreateProductComponent,
    ImageComponent,
    CreateImageComponent,
    ShopComponent,
    ShopDetailComponent,
    CartComponent,
    CheckoutComponent,
    OrdersComponent,
    OrderdetailsComponent,
    UserComponent,
    UserorderhistoryComponent,
    DetailUseroderhistoryComponent,
  ],
  bootstrap: [MainComponent],
})
export class AppModule {
  constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
