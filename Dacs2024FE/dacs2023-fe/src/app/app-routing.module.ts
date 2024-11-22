import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guard/auth.guard';
import { MisLibrosComponent } from './mis-libros/mis-libros.component';
import { LibrosCompartiblesComponent } from './libros-compartibles/libros-compartibles.component';
import { BuscarLibroComponent } from './buscar-libro/buscar-libro.component';

const routes: Routes = [
  { path: '', canActivate: [AuthGuard]},
  { path: 'mis-libros', component: MisLibrosComponent},
  { path: 'libros-compartibles', component: LibrosCompartiblesComponent },
  { path: 'buscar-libro', component: BuscarLibroComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({ 
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
