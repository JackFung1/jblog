import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JblogBlogModule } from './blog/blog.module';
import { JblogEntryModule } from './entry/entry.module';
import { JblogTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JblogBlogModule,
        JblogEntryModule,
        JblogTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogEntityModule {}
