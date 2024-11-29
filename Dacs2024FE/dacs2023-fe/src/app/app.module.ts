import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from './core/init/keycloak-init.factory';
import { ApiService } from './core/services/apiservice.service';
import { HttpClientModule } from '@angular/common/http';
import { MisLibrosComponent } from './mis-libros/mis-libros.component';
import { FormsModule } from '@angular/forms';
import { LibrosCompartiblesComponent } from './libros-compartibles/libros-compartibles.component';
import { BuscarLibroComponent } from './buscar-libro/buscar-libro.component';
import { DevolverLibrosComponent } from './devolver-libros/devolver-libros.component';
import { FeedbackLibrosComponent } from './feedback-libros/feedback-libros.component';
@NgModule({
  declarations: [
    AppComponent,
    MisLibrosComponent,
    LibrosCompartiblesComponent,
    BuscarLibroComponent,
    DevolverLibrosComponent,
    FeedbackLibrosComponent,
  ],
  imports: [
    BrowserModule,
    KeycloakAngularModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    ApiService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
