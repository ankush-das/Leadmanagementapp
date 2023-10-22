export class SignUpRequest {
    username: string;
    password: string;
    email: string;
    companyname: string;
    phone: string;
    role: string;
    teamname: string;

    constructor(data: any = {}) {
        this.username = data.username || '';
        this.password = data.password || '';
        this.email = data.email || '';
        this.companyname = data.companyname || '';
        this.phone = data.phone || '';
        this.role = data.role || '';
        this.teamname = data.teamname || '';
    }
}
