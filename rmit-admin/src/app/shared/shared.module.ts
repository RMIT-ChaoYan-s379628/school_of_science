import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from './components//navigation/navigation.component';
import { NotFoundComponent } from './components//not-found/not-found.component';
import { AuthService } from './services/auth.service';
import { LoadingComponent } from './components/loading/loading.component';
import { CanActivateSection } from './services/can-activate.service';
import { PaginationComponent } from './components/pagination/pagination.component';
import { RouterModule } from '@angular/router';
import { UtilityService } from './services/utility.service';
import { ModalComponent } from './components/modal/modal.component';
import { SearchComponent } from './components/search/search.component';


@NgModule({
    imports: [
        CommonModule,
        RouterModule,
    ],
    declarations: [
        NavigationComponent, 
        NotFoundComponent, LoadingComponent, PaginationComponent, ModalComponent, SearchComponent
    ],
    providers: [
        AuthService,
        CanActivateSection,
        UtilityService
    ],
    exports: [
        NavigationComponent,
        NotFoundComponent,
        LoadingComponent,
        PaginationComponent,
        ModalComponent,
        SearchComponent
    ]
})
export class SharedModule { }
