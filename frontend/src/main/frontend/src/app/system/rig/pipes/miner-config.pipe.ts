import {Pipe, PipeTransform} from "@angular/core";
import {MinerConfig} from "../../../shared/models/minerConfig.model";

@Pipe({
    name: 'appMinerConfigPipe'
})
export class MinerConfigPipe implements PipeTransform {

    transform(value: MinerConfig) {
        return value ? value.name : value;
    }

}