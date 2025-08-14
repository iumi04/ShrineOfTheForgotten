package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // check if the entity is colliding with a tile...if they are, set collisionOn to true
        
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize; 
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;  
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
        
        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                //when the player is moving up, we need to check the tile above the player
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize; 
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow]; //check left and right shoulders of player

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true; 
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize; 
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; //check left and right feet of player

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true; 
                }
                break;
            case "left":
                 entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize; 
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow]; //check top left and bottom left of player

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true; 
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize; 
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow]; //check top right and bottom right of player

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true; 
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player) {
        //check if the player is colliding with an object...if they are, return the index of the object

        int index = 999; //you can use any index that is not used by the objects array [1-10].. 999 is obv not used.

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed; 
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            // if the entity's solid area intersects with the object's solid area
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true; 
                            }
                            if (player == true) {
                                index = i; 
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true; 
                            }
                            if (player == true) {
                                index = i; 
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true; 
                            }
                            if (player == true) {
                                index = i; 
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true; 
                            }
                            if (player == true) {
                                index = i; 
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX; 
                entity.solidArea.y = entity.solidAreaDefaultY; 
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
