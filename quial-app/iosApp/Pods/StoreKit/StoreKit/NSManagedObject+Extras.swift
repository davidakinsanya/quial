//
//  NSManagedObject+Extras.swift
//  JSQCoreDataKit
//
//  Created by Doug Mason on 9/6/15.
//  Copyright © 2015 Hexed Bits. All rights reserved.
//

import Foundation
import CoreData

public extension NSManagedObject {
    
    /**
    Convenience static function to grab name of an entity to make life easier when creating an NSEntityDescription instance especially on
    NSManagedObject subclass's during initialization.
    */
    static var entityName: String {
        let fullClassName = NSStringFromClass(object_getClass(self)!)
        let nameComponents = fullClassName.split { $0 == "." }
                                                     .map { String($0) }
        
        return nameComponents.last!
    }
    
    /**
        This convenience initializer makes it easier to init an NSManagedObject subclass without needing to
        provide boiler plate code to setup an entity description in every subclass init method; simply make a call to init on self in your subclass.
    
        :param: context The NSManagedObjectContext to init the subclassed NSManagedObject with.
    */
    convenience init(context: NSManagedObjectContext) {
        let entityName = Self.entityName
        let entity = NSEntityDescription.entity(forEntityName: entityName, in: context)!
        self.init(entity: entity, insertInto: context)
    }
}
