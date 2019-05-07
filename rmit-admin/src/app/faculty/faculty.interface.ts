export interface FeedData {
    authorId: string;
    category: "DEADLINES" | "NEWS" | "EVENTS";
    createdDate: number;
    deadlineDate: any;
    deleted: boolean;
    department: string;
    eventTagline: string;
    feedId: string;
    news: string;
    sendNotification: boolean;
    title: boolean;
    updatedDate: number;
}

export interface FeedBodyFields {
    key: string;
    type: any;
    title: string;
}

export interface ContactData {
    contactId: string;
    createdDate: number;
    deleted: boolean;
    department: string;
    emailId: string;
    name: string;
    phoneNo: string;
    updatedDate: number;
}

export interface UserData {
    createdDate: number;
    department: string;
    name: string;
    status: string;
    updatedDate: number;
    userId: string;
}
