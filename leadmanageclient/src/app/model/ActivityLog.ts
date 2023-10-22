import { LeadCaptureDTO } from "./LeadCaptureDTO";
import { UserDTO } from "./UserDTO";

export class ActivityLog {
    id: number;
    logDate: Date;
    details: string;
    user: UserDTO;
    lead: LeadCaptureDTO;

    constructor(
        id: number,
        logDate: Date,
        details: string,
        user: UserDTO,
        lead: LeadCaptureDTO
    ) {
        this.id = id;
        this.logDate = logDate;
        this.details = details;
        this.user = user;
        this.lead = lead;
    }
}
