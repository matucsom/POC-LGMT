export interface IUser {
    id:number,
    username: string
}

export interface iNote{
    id: number,
    title: string,
    text: string,
    archived: boolean
    categoryid:number
}

export interface iNoteComplete extends iNote{
    userid: number,
}

export interface iCategory{
}