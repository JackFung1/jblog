import { BaseEntity } from './../../shared';

export class Entry implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public date?: any,
        public blogId?: number,
        public tags?: BaseEntity[],
    ) {
    }
}
