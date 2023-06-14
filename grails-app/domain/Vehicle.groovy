

import java.time.LocalDateTime

class Vehicle {
    String brandName
    String modelName
    String spec

    static constraints = {
        brandName       maxSize: 30
        modelName       maxSize: 30

        spec            maxSize: 30
    }
}
