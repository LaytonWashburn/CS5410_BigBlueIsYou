package ecs.Entities;

import utils.Properties;
import utils.NounType;

public class Rock {

    public static Entity create(float posX, float posY, boolean pushable){

        Entity rock = new Entity();

        rock.add(new ecs.Components.Sprite());
        rock.add(new ecs.Components.Position(posX, posY));
        rock.add(new ecs.Components.Noun(NounType.FLAG));
        if(pushable){
            rock.add(new ecs.Components.Property(Properties.PUSHABLE));
        }

        return rock;

    }
}
