import {MinerConfig} from "./minerConfig.model";
import {Status} from "./status.model";

export class Rig {
    id: string;
    name: string = "";
    password: string = "";
    status: Status = null;
    minerConfig: MinerConfig = null;
}
