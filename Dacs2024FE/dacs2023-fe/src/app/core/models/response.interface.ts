export interface IResponse
    {
        error : boolean ;
        errorCode: string;
        errorDescription: string;
        data: any;
    }

    export interface ITestResponse
    {
        version: string;
    }