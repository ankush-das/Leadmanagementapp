import { UserDTO } from "./UserDTO";

export class LeadDTO {
    constructor(
        public quote: string = '',
        public budget: number = 0,
        public probability: string = '',
        public priority: string = '',
        public stage: string = '',
        public source: string = '',
        public tag: string = '',
        public expectedClosingDate?: Date,
        public assignedUser: UserDTO = new UserDTO()
    ) { }
}
