### Backend
```
spring init backend
```
pom.xml
```xml
<java.version>10</java.version>
<artifactId>spring-boot-starter-web</artifactId>
```
DemoController.java
```java
@RestController
public class DemoController {

    @GetMapping("/user")
    public Sample getUser() {
        return new Sample();
    }
}

@JsonAutoDetect(fieldVisibility = ANY)
class Sample {
    public UUID id = UUID.randomUUID();
    public long timestamp = System.currentTimeMillis();
}
```
#### Enable Swagger
DemoApplication.java
```java
@SpringBootApplication
@EnableSwagger2
public class DemoApplication {
	@Bean
	public Docket api() {
		String basePackage = DemoApplication.class.getPackageName();
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.build();
	}
	/*...*/
}
```
sample.http
```
GET http://localhost:8080/user
###
GET http://localhost:8080/v2/api-docs
###
```
### Frontend 
```
create-react-app frontend --typescript
```

api.sh:
```bash
#!/bin/bash
set -eu
cd $(dirname "$0")

docker run --network host --user $(id -u):$(id -g) --rm -v $PWD:/pwd \
   swaggerapi/swagger-codegen-cli generate \
   -i http://localhost:8080/v2/api-docs \
   -l typescript-fetch \
   -o /pwd/src/api

rm -vr src/api/.[!.]* src/api/*.sh
```

Generated code uses `portable-fetch` package: 
```bash
yarn add portable-fetch
```
tsconfig.json (for generated code compat):
```json
"strictPropertyInitialization": false,
```

package.json (so no cors):
```json
"proxy": "http://localhost:8080",
```

App.tsx
```tsx
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

```
