import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const WebContentMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.web-content.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    {/*<MenuItem icon="asterisk" to="/service-option">*/}
    {/*  <Translate contentKey="global.menu.web-content.serviceOption" />*/}
    {/*</MenuItem>*/}
    {/*<MenuItem icon="asterisk" to="/policy">*/}
    {/*  <Translate contentKey="global.menu.web-content.policy" />*/}
    {/*</MenuItem>*/}
    {/*<MenuItem icon="asterisk" to="/img-url">*/}
    {/*  <Translate contentKey="global.menu.entities.imgUrl" />*/}
    {/*</MenuItem>*/}
    {/*<MenuItem icon="asterisk" to="/process">*/}
    {/*  <Translate contentKey="global.menu.entities.process" />*/}
    {/*</MenuItem>*/}
    <MenuItem icon="asterisk" to="/service-item">
      <Translate contentKey="global.menu.web-content.serviceItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/achievement">
      <Translate contentKey="global.menu.web-content.achievement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/customer-review">
      <Translate contentKey="global.menu.web-content.customerReview" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/blog">
      <Translate contentKey="global.menu.web-content.blog" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/singleton-content">
      <Translate contentKey="global.menu.web-content.singletonContent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/video">
      <Translate contentKey="global.menu.entities.video" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add web-content to the menu here */}
  </NavDropdown>
);
