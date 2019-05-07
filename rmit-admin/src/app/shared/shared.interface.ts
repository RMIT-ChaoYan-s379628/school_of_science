export interface GlobalResponse {
    code: number;
    message: string;
}

export enum ResponseCodes {
    success = 200,
    error = 400,
}

export interface UserDetails {
    role: string;
    userId: string;
}

export const EmailRegExp: RegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;