package iot.domain;

/**
 * Created by Geert on 2017-01-18.
 */
public enum DeploymentType {
    Broker(1),
    Distributed(2),
    Unknown(-1);

    private int type;

    DeploymentType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    /**
     * Parses the provided integer deployment type to an instance of the enumeration.
     * @param type The integer type that is to be parsed.
     * @return The deployment type matching the provided integer. If no match is found, then Unknown is returned.
     */
    public static DeploymentType Parse(int type) {
        switch (type) {
            case 1:
                return Broker;
            case 2:
                return Distributed;
            default:
                return Unknown;
        }
    }
}
