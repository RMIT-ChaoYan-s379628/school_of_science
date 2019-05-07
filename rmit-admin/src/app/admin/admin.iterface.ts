export interface UserInterface {
    department: string;
    name: string;
    userId: string;
}

export interface UserForm {
    errors: string[];
    data: UserInterface;
    isValid: boolean;
}
