export interface FacultyData {
    createdDate: number;
    department: string;
    name: string;
    status: string;
    updatedDate: number;
    userId: string;
}

export interface GetUsersObject {
    code: number;
    message: string;
    size: number;
    facultyList: FacultyData[];
}
