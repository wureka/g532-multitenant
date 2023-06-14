import grails.gorm.MultiTenant;

class Tuser implements MultiTenant<Tuser> {
    String userid

    static hasMany = [
            vehicles: Vehicle
    ]

    static constraints = {
        userid          maxSize: 20

    }
}
