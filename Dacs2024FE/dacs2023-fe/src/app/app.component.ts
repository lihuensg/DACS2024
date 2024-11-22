import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
import { ApiService } from './core/services/apiservice.service';
import { ITestResponse } from './core/models/response.interface';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'dacs2023';
  public isLogueado = false;
  public testResponse : ITestResponse  | null = null;
  public apiPing = "";
  public apiConectorPing = "";
  public apiBackendPing = "";
  public perfilUsuario: KeycloakProfile | null = null;
  public role = false;
  public alumnos = {};
  
  constructor(private readonly keycloak: KeycloakService,private apiService: ApiService) {}

  public async ngOnInit() {

    this.isLogueado = await this.keycloak.isLoggedIn();
    this.role=await this.keycloak.isUserInRole("ROLE-A");
    this.apiService.getTest().subscribe(resp => {this.testResponse= resp});
    this.apiService.getPing().subscribe(resp => {this.apiPing= resp});
    this.apiService.getConectorPing().subscribe(resp => {this.apiConectorPing= resp});
    this.apiService.getBackendPing().subscribe(resp => {this.apiBackendPing= resp});
    this.apiService.getAlumnos().subscribe(resp => {this.alumnos= resp});
    
    console.log ("role=====>", this.role );
    if(this.isLogueado && !this.role){
      this.keycloak.logout();
      return;
    }
    type rolesUsuarios = Array<{id: number, text: string}>;

    if (this.isLogueado) {
      this.perfilUsuario = await this.keycloak.loadUserProfile();
    }
  }

  public iniciarSesion() {
    this.keycloak.login();
  }

  public cerrarSesion() {
    this.keycloak.logout();
  }
}
