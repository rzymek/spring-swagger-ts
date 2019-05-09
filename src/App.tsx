import React, {useEffect, useState} from 'react';
import {DemoControllerApi, Sample} from "./api";

const App: React.FC = () => {
    const [resp, setResp] = useState<Sample>({} as any);
    useEffect(() => {
        new DemoControllerApi({}, "")
            .getUserUsingGET()
            .then(resp => setResp(resp));
    }, []);
    return (
        <div>
            <h1>{resp.id}</h1>
            <h2>{resp.timeStamp}</h2>
        </div>
    );
}

export default App;
