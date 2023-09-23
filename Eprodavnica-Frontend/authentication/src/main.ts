import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

/// @ts-ignore
require('node_modules/@angular/material/prebuilt-themes/deeppurple-amber.css?ngGlobalStyle');
/// @ts-ignore
require('node_modules/bootstrap/dist/css/bootstrap.min.css?ngGlobalStyle');
/// @ts-ignore
require('node_modules/ngx-loading-buttons/src/styles.css?ngGlobalStyle');
